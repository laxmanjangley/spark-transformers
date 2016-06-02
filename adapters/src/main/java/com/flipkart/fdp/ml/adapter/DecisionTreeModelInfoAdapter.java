package com.flipkart.fdp.ml.adapter;

import com.flipkart.fdp.ml.modelinfo.DecisionTreeModelInfo;
import com.flipkart.fdp.ml.modelinfo.DecisionTreeModelInfo.DecisionNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.apache.spark.mllib.tree.model.Node;
import org.apache.spark.mllib.tree.model.Split;
import org.apache.spark.sql.DataFrame;

import java.util.Stack;

/**
 * Transforms Spark's {@link DecisionTreeModel} in MlLib to  {@link com.flipkart.fdp.ml.modelinfo.DecisionTreeModelInfo} object
 * that can be exported through {@link com.flipkart.fdp.ml.export.ModelExporter}
 */
@Slf4j
public class DecisionTreeModelInfoAdapter
        extends AbstractModelInfoAdapter<DecisionTreeModel, DecisionTreeModelInfo> {

    private void visit(final Node node, final Stack<Node> nodesToVisit, final DecisionTreeModelInfo treeInfo) {
        final DecisionNode nodeInfo = new DecisionNode();
        nodeInfo.setId(node.id());
        nodeInfo.setLeaf(node.isLeaf());
        if (node.split().nonEmpty()) {
            final Split split = node.split().get();
            nodeInfo.setFeature(split.feature());
            nodeInfo.setThreshold(split.threshold());
            nodeInfo.setFeatureType(split.featureType().toString());
        }
        nodeInfo.setPredict(node.predict().predict());
        nodeInfo.setProbability(node.predict().prob());
        treeInfo.getNodeInfo().put(node.id(), nodeInfo);
        if (node.rightNode().nonEmpty()) {
            final Node right = node.rightNode().get();
            treeInfo.getRightChildMap().put(node.id(), right.id());
            nodesToVisit.push(right);
        }
        if (node.leftNode().nonEmpty()) {
            final Node left = node.leftNode().get();
            treeInfo.getLeftChildMap().put(node.id(), left.id());
            nodesToVisit.push(left);
        }
    }

    public DecisionTreeModelInfo getModelInfo(final DecisionTreeModel decisionTreeModel, DataFrame df) {
        final DecisionTreeModelInfo treeInfo = new DecisionTreeModelInfo();
        final Node node = decisionTreeModel.topNode();
        treeInfo.setRoot(node.id());
        final Stack<Node> nodesToVisit = new Stack<>();
        nodesToVisit.push(node);
        while (!nodesToVisit.empty()) {
            Node curr = nodesToVisit.pop();
            visit(curr, nodesToVisit, treeInfo);
        }
        return treeInfo;
    }

    @Override
    public Class<DecisionTreeModel> getSource() {
        return DecisionTreeModel.class;
    }

    @Override
    public Class<DecisionTreeModelInfo> getTarget() {
        return DecisionTreeModelInfo.class;
    }
}

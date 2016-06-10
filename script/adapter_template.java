package com.flipkart.fdp.ml.adapter;







import com.flipkart.fdp.ml.modelinfo.<XXX>ModelInfo;
import org.apache.spark.ml.feature.<XXX>;
import org.apache.spark.sql.DataFrame;



import java.util.LinkedHashSet;
import java.util.Set;



/**
 * Transforms Spark's {@link <XXX>} in MlLib to  {@link com.flipkart.fdp.ml.modelinfo.<XXX>ModelInfo} object
 * that can be exported through {@link com.flipkart.fdp.ml.export.ModelExporter}
 */
public class <XXX>ModelInfoAdapter extends AbstractModelInfoAdapter<<XXX>, <XXX>ModelInfo> {



    @Override
    public <XXX>ModelInfo getModelInfo(final <XXX> from, final DataFrame df) {
        final <XXX>ModelInfo modelInfo = new <XXX>ModelInfo();
        modelInfo.set<Field1>(from.get<Field1>());
        Set<String> inputKeys = new LinkedHashSet<String>();
        inputKeys.add(from.getInputCol());
        modelInfo.setInputKeys(inputKeys);
        modelInfo.setOutputKey(from.getOutputCol());
        return modelInfo;
    }



    @Override
    public Class<<XXX>> getSource() {
        return <XXX>.class;
    }



    @Override
    public Class<<XXX>ModelInfo> getTarget() {
        return <XXX>ModelInfo.class;
    }
}

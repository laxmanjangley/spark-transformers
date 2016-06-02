package com.flipkart.fdp.ml.adapter;

/**
 * Created by root on 31/5/16.
 */
import com.flipkart.fdp.ml.modelinfo.ExpressionEvalModelInfo;
import org.apache.spark.sql.DataFrame;
import spark.examples.ExpressionEval;

/**
 * Transforms Spark's {@link ExpressionEval} in MlLib to  {@link com.flipkart.fdp.ml.modelinfo.ExpressionEvalModelInfo} object
 * that can be exported through {@link com.flipkart.fdp.ml.export.ModelExporter}
 */
public class ExpressionEvalModelInfoAdapter extends AbstractModelInfoAdapter<ExpressionEval, ExpressionEvalModelInfo> {
    @Override
    public ExpressionEvalModelInfo getModelInfo(final ExpressionEval from, DataFrame df) {
        final ExpressionEvalModelInfo modelInfo = new ExpressionEvalModelInfo();
        modelInfo.setNumFeatures(from.getNumFeatures());
        modelInfo.setExpr(from.getExpr());
        modelInfo.setFunction(from.getFunction());
        modelInfo.setOutputCol(from.getOutputCol());
        modelInfo.setInputCols(from.getInputCols());
        return modelInfo;
    }

    @Override
    public Class<ExpressionEval> getSource() {
        return ExpressionEval.class;
    }

    @Override
    public Class<ExpressionEvalModelInfo> getTarget() {
        return ExpressionEvalModelInfo.class;
    }
}


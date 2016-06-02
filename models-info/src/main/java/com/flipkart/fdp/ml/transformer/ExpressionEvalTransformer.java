package com.flipkart.fdp.ml.transformer;

import com.flipkart.fdp.ml.modelinfo.AbstractModelInfo;
import com.flipkart.fdp.ml.modelinfo.ExpressionEvalModelInfo;
import scala.Function1;
import java.util.Map;

/**
 * Transforms input/ predicts for a ExpressionEval model representation
 * captured by  {@link com.flipkart.fdp.ml.modelinfo.ExpressionEvalModelInfo}.
 */
public class ExpressionEvalTransformer implements Transformer {

    private final ExpressionEvalModelInfo modelInfo;

    public ExpressionEvalTransformer(final ExpressionEvalModelInfo modelInfo) {
        this.modelInfo = modelInfo;
    }
    public Object[] predict(Object[][] inp) {
        int l1 = inp.length;
        int l2 = inp[0].length;
        Double[] output = new Double[l2];
        for(int i=0;i<l2;i++) {
            String exp = modelInfo.getExpr();
            for(int j=0; j<l1; j++){
                String s  = modelInfo.getInputCols()[j];
                exp = exp.replace(s, inp[j][i].toString());
            }
            Function1<String, Object> f = modelInfo.getFunction();
            output[i] = (Double) f.apply(exp);
        }
        return output;
    }


    @Override
    public void transform(Map<String, Object> input) {
        Double[][] inp = new Double[modelInfo.getInputCols().length][];
        for (int i=0; i < modelInfo.getInputCols().length; i++){
            inp[i] = (Double[]) input.get(modelInfo.getInputCols()[i]);
        }
        input.put(modelInfo.getOutputKey(), predict(inp));
    }

}
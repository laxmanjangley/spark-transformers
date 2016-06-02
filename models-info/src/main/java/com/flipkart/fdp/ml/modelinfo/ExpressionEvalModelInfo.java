package com.flipkart.fdp.ml.modelinfo;

import com.flipkart.fdp.ml.transformer.ExpressionEvalTransformer;
import com.flipkart.fdp.ml.transformer.Transformer;
import lombok.Data;
import scala.Function1;

/**
 * Created by root on 31/5/16.
 */
@Data
public class ExpressionEvalModelInfo extends AbstractModelInfo {
    private int numFeatures;
    private String expr;
    private String outputCol;
    private Function1<String, Object> function;
    private String[] inputCols;

    /**
     * @return an corresponding {@link ExpressionEvalTransformer} for this model info
     */
    @Override
    public Transformer getTransformer() {
        return new ExpressionEvalTransformer(this);
    }
}

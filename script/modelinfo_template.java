
package com.flipkart.fdp.ml.modelinfo;

import com.flipkart.fdp.ml.transformer.<XXX>Transformer;
import com.flipkart.fdp.ml.transformer.Transformer;
import lombok.Data;

/**
 * Represents information for a <XXX> model
 */
@Data
public class <XXX>ModelInfo extends AbstractModelInfo {
    private <Field1DataType> <Field1>;
    /**
     * @return an corresponding {@link <XXX>Transformer} for this model info
     */
    @Override
    public Transformer getTransformer() {
        return new <XXX>Transformer(this);
    }
}

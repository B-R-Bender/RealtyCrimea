package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.vk.api.interfaces.VkApiMethod;

/**
 * Created by Bender on 24.11.2016.
 */
public enum VkProductApiMethods implements VkApiMethod {
    marketAdd {
        @Override
        public String getExactMethod() {
            return "market.add";
        }

        @Override
        public String getMethodName() {
            return null;
        }
    },
    marketDelete {
        @Override
        public String getExactMethod() {
            return "market.delete";
        }

        @Override
        public String getMethodName() {
            return null;
        }
    },
    marketEdit {
        @Override
        public String getExactMethod() {
            return "market.edit";
        }

        @Override
        public String getMethodName() {
            return null;
        }
    };

}

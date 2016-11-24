package agency.realtycrimea.vk.api;

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
    },
    marketDelete {
        @Override
        public String getExactMethod() {
            return "market.delete";
        }
    },
    marketEdit {
        @Override
        public String getExactMethod() {
            return "market.edit";
        }
    };

}

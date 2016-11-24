package agency.realtycrimea.vk.utility;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.vk.model.VkAbstractObject;
import agency.realtycrimea.vk.utility.interfaces.VkRequestCreator;

/**
 * Created by Bender on 24.11.2016.
 */
public class VkSimpleRequestCreator implements VkRequestCreator {
    @Override
    public SimpleRequest fabricMethod(VkAbstractObject object, Enum apiMethod) {
        return null;
    }
}

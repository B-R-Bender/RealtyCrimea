package agency.realtycrimea.vk.utility.interfaces;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.vk.model.VkAbstractObjectRename;

/**
 * Created by Bender on 24.11.2016.
 */
public interface VkRequestCreator {

    SimpleRequest fabricMethod(VkAbstractObjectRename object, Enum apiMethod);

}

package agency.realtycrimea.vk.utility.interfaces;

import agency.realtycrimea.vk.model.VkAbstractObject;

/**
 * Created by Bender on 23.11.2016.
 */
public interface VkObjectCreator {

    VkAbstractObject fabricMethod(Object resource);

}
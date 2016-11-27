package agency.realtycrimea.vk.utility.interfaces;

import agency.realtycrimea.vk.model.VkAbstractObject;

import java.util.List;

/**
 * Created by Bender on 23.11.2016.
 */
public interface VkObjectCreator {

    List<? extends VkAbstractObject> fabricMethod(Object resource);

}
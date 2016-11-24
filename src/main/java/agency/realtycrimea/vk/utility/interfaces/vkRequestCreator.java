package agency.realtycrimea.vk.utility.interfaces;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.vk.model.vkAbstractObject;

/**
 * Created by Bender on 24.11.2016.
 */
public interface vkRequestCreator {

    SimpleRequest fabricMethod(vkAbstractObject object, Enum apiMethod);

}

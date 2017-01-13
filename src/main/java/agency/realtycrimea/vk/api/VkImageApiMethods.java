package agency.realtycrimea.vk.api;

import agency.realtycrimea.network.SimpleRequest;
import agency.realtycrimea.vk.api.interfaces.VkApiMethod;
import agency.realtycrimea.vk.model.VkImage;
import agency.realtycrimea.vk.utility.AppProperty;
import com.sun.webkit.network.URLs;
import org.apache.http.entity.ContentType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Bender on 24.11.2016.
 */
public enum VkImageApiMethods implements VkApiMethod {

    /**
     * Метод для получения адреса сервера для загркзкифотографий товара
     */
    photosGetMarketUploadServer() {
        @Override
        public String getExactMethod() {
            return String.format(VkApiMethod.BASE_API_METHOD,
                    getMethodName(),
                    getParameterString(),
                    VkAuthMethods.getToken(),
                    AppProperty.properties.getProperty("vk.api.version"));
        }

        @Override
        public String getMethodName() {
            return "photos.getMarketUploadServer";
        }

        private String getParameterString() {
            return "group_id=" + AppProperty.properties.getProperty("vk.group.id")
                    + "&main_photo=" + (currentImage.isMainPhoto() ? 1 : 0)
                    + (currentImage.getCropX() != null ? "&crop_x=" + currentImage.getCropX() : "")
                    + (currentImage.getCropY() != null ? "&crop_y=" + currentImage.getCropY() : "")
                    + (currentImage.getCropWidth() != null ? "&crop_width=" + currentImage.getCropWidth() : "");
        }
    },
    /**
     * Метод для загрузки изображения на сервер vk
     */
    photoUpload {
        @Override
        public String getExactMethod() {
            setParameters();
            return getMethodName();
        }

        @Override
        public String getMethodName() {
            return currentImage.getUploadServerURI();
        }

        private void setParameters() {
            File tempFileForUpload = null;
            String groupId = currentImage.getGroupId();
            groupId = groupId.charAt(0) == '-' ? groupId.substring(1) : groupId;

            try (InputStream watermarkStream = VkImageApiMethods.class.getClassLoader().getResourceAsStream("watermarks/" + groupId + ".png");) {
                tempFileForUpload = new File("temp.jpg");
                BufferedImage sourceImage = ImageIO.read(URLs.newURL(currentImage.getImageURL()));

                if (watermarkStream != null) {
                    BufferedImage watermarkImage = ImageIO.read(watermarkStream);

                    // initializes necessary graphic properties
                    Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
                    AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
                    g2d.setComposite(alphaChannel);

                    // calculates the coordinate where the image is painted
                    int topLeftX = (sourceImage.getWidth() - watermarkImage.getWidth()) / 2;
                    int topLeftY = (sourceImage.getHeight() - watermarkImage.getHeight()) / 2;

                    // paints the image watermark
                    g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);
                    ImageIO.write(sourceImage, "jpg", tempFileForUpload);
                    g2d.dispose();
                } else {
                    //TODO: сообщение о том что беда с наложением ватермарка
                    System.out.println("Ватермарк не наложился");
                    ImageIO.write(sourceImage, "jpg", tempFileForUpload);
                }

            } catch (IOException e) {
                //TODO: error to log
                e.printStackTrace();
                tempFileForUpload.delete();
            }

            methodParameters.put("contentType", ContentType.MULTIPART_FORM_DATA);
            methodParameters.put("file", tempFileForUpload);
        }

        @Override
        public SimpleRequest.RequestType getMethodRequestType() {
            return SimpleRequest.RequestType.POST;
        }
    },
    /**
     * Метод для сохранения загруженного фото как фото товара
     */
    photosSaveMarketPhoto {
        @Override
        public String getExactMethod() {
            return String.format(VkApiMethod.BASE_API_METHOD,
                    getMethodName(),
                    getParameterString(),
                    VkAuthMethods.getToken(),
                    AppProperty.properties.getProperty("vk.api.version"));
        }

        @Override
        public String getMethodName() {
            return "photos.saveMarketPhoto";
        }

        private String getParameterString() {
            try {
                return "group_id=" + AppProperty.properties.getProperty("vk.group.id")
                        + "&photo=" + URLEncoder.encode(currentImage.getPhoto(), "UTF-8")
                        + "&server=" + currentImage.getServer()
                        + "&hash=" + currentImage.getHash()
                        + (currentImage.getCropData() != null ? "&crop_data=" + currentImage.getCropData() : "")
                        + (currentImage.getCropHash() != null ? "&crop_hash=" + currentImage.getCropHash() : "");
            } catch (UnsupportedEncodingException e) {
                //TODO: error to log
                e.printStackTrace();
            }
            return "";
        }
    },
    photoDelete{
        @Override
        public String getExactMethod() {
            return String.format(VkApiMethod.BASE_API_METHOD,
                    getMethodName(),
                    getParameterString(),
                    VkAuthMethods.getToken(),
                    AppProperty.properties.getProperty("vk.api.version"));
        }

        @Override
        public String getMethodName() {
            return "photos.delete";
        }

        private String getParameterString() {
            return "owner_id=" + currentImage.getVkImageObject().get("owner_id")
                    + "&photo_id=" + currentImage.getVkImageObject().get("id");
        }

    };

    private static VkImage currentImage;

    public static void setCurrentImage(VkImage currentImage) {
        VkImageApiMethods.currentImage = currentImage;
    }

    public VkImage getCurrentImage() {
        return currentImage;
    }
}

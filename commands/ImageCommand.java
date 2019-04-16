import net.rodrigocarvalho.lucy.command.model.AbstractCommand;
import net.rodrigocarvalho.lucy.command.model.CommandEvent;
import net.rodrigocarvalho.lucy.command.model.CommandHandler;
import net.rodrigocarvalho.lucy.utils.BotUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

@CommandHandler(name = "image")
public class ImageCommand extends AbstractCommand {

    @Override
    public void execute(CommandEvent event) {
        try {
            File file = new File("./teste.jpg");
            BufferedImage image = ImageIO.read(file);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.ITALIC, 30));
            graphics.drawString("Gostosa", image.width()/2, image.height()/2);
            graphics.dispose();
	    File newImage = new File("./nova_imagem.jpg");
            ImageIO.write(image, "jpg", newImage);
            event.getChannel().sendFile(newImage).queue();
        } catch (Exception e) {
            e.printStackTrace();
            event.sendMessage("Erro la doido");
        }
    }
}
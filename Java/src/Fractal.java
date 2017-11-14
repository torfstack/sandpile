import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Fractal {

	private Sandpile sp;
	private BufferedImage img;

	private final int LIGHTBLUE = 0x41719f;
	private final int PURPLE = 0x3616b8;
	private final int DARKRED = 0x7e0000;
	private final int ORANGE = 0xcf7034;

	public Fractal(Sandpile sp) {
		this.sp = sp;
	}

	public void generateImg() {
		this.sp.normalize();
		this.img = new BufferedImage(this.sp.getDim(), this.sp.getDim(), BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < this.sp.getDim(); ++i) {
			for (int j = 0; j < this.sp.getDim(); ++j) {
				if (sp.get(i,j) == 0) img.setRGB(i,j,PURPLE);
				if (sp.get(i,j) == 1) img.setRGB(i,j,ORANGE);
				if (sp.get(i,j) == 2) img.setRGB(i,j,LIGHTBLUE);
				if (sp.get(i,j) == 3) img.setRGB(i,j,DARKRED);
			}	
		}
		System.out.println("Generated image");
	}

	public void saveImg(String path) {
		File f = new File(path);
		try {
			ImageIO.write(img, "PNG", f);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
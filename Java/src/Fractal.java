import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class Fractal {

	private Sandpile sp;
	private BufferedImage img;
	private boolean generated;

	private final int BLACK = 0;
	private final int BLUE = 0xFF;
	private final int GREEN = 0xFF << 8;
	private final int RED = 0xFF << 16;

	public Fractal(Sandpile sp) {
		this.sp = sp;
		this.img = new BufferedImage(this.sp.getDim(), this.sp.getDim(), 
			BufferedImage.TYPE_INT_RGB);
		this.generated = false;
	}

	public void generateImg() {
		if (!this.generated) {
			this.sp.normalize();

			for (int i = 0; i < this.sp.getDim(); ++i) {
				for (int j = 0; j < this.sp.getDim(); ++j) {
					if (sp.get(i,j) == 0) img.setRGB(i,j,BLACK);
					if (sp.get(i,j) == 1) img.setRGB(i,j,RED);
					if (sp.get(i,j) == 2) img.setRGB(i,j,BLUE);
					if (sp.get(i,j) == 3) img.setRGB(i,j,GREEN);
				}	
			}
		}
	}

	public void saveImg(String path) {
		if (!this.generated) this.generateImg();
		File f = new File(path);
		try {
			ImageIO.write(img, "PNG", f);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
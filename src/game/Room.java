package game;
    public class Room{
        
        private int depth;
        private int height;
        private int width;
        private String textureTop;
        private String textureBottom;
        private String textureNorth;
        private String textureSouth;
        private String textureEast;
        private String textureWest;
              
        public Room(){
            this.depth = 100;
            this.width = 100;
            this.height = 60;
            
            this.textureTop = "textures/skybox/default/top.png";
            this.textureBottom = "textures/skybox/default/bottom.png";
            
            this.textureNorth = "textures/skybox/default/north.png";

            this.textureEast = "textures/skybox/default/east.png";
            this.textureWest = "textures/skybox/default/west.png";  
            
             
        }

    public int getDepth() {
        return depth;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }
        
    
    }
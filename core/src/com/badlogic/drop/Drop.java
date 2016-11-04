package com.badlogic.drop;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Drop implements ApplicationListener {
   Texture laserImage;
   Texture spaceImage;
   Texture bossImage;
   Texture coinImage;
   Sound laserSound;
   Sound coinSound;
   Music starMusic;
   SpriteBatch batch;
   OrthographicCamera camera;
   Rectangle space;
   Rectangle laser;
   Rectangle coin;
   Rectangle boss;
   Array<Rectangle> lasers;
   Array<Rectangle> coins;
   long lastDropTime;

   @Override
   public void create() {
      
      laserImage = new Texture(Gdx.files.internal("laser.png"));
      coinImage = new Texture(Gdx.files.internal("coin.png"));
      bossImage = new Texture(Gdx.files.internal("boss.png"));
      spaceImage = new Texture(Gdx.files.internal("space.png"));

      
      laserSound = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));
      starMusic = Gdx.audio.newMusic(Gdx.files.internal("star.mp3"));

      
      starMusic.setLooping(true);
      starMusic.play();

      
      camera = new OrthographicCamera();
      camera.setToOrtho(false, 800, 480);
      batch = new SpriteBatch();

      
      space = new Rectangle();
      space.x = 800 / 2 - 64 / 2; 
      space.y = 20; 
      space.width = 64;
      space.height = 64;
      
      boss = new Rectangle();
      boss.x = 800 / 2 - 64 / 2; 
      boss.y = 400; 
      boss.width = 64;
      boss.height = 64;

      
      lasers = new Array<Rectangle>();
      spawnLaser();
      
      coins = new Array<Rectangle>();
      spawnCoin();
   }

   private void spawnLaser() {
      Rectangle laser = new Rectangle();
      laser.x = MathUtils.random(0, 800-64);
      laser.y = 480;
      laser.width = 64;
      laser.height = 64;
      lasers.add(laser);
      lastDropTime = TimeUtils.nanoTime();
   }
   
   private void spawnCoin() {
	      Rectangle coin = new Rectangle();
	      coin.x = MathUtils.random(0, 800-64);
	      coin.y = 480;
	      coin.width = 64;
	      coin.height = 64;
	      coins.add(coin);
	      lastDropTime = TimeUtils.nanoTime();
	   }

   @Override
   public void render() {
      
      Gdx.gl.glClearColor(0, 0, 0.2f, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      
      camera.update();

      
      batch.setProjectionMatrix(camera.combined);

      
      batch.begin();
      batch.draw(spaceImage, space.x, space.y);
      batch.draw(bossImage, boss.x, boss.y);
      for(Rectangle laser: lasers) {
          batch.draw(laserImage, laser.x, laser.y);
       }
      for(Rectangle laser: lasers) {
         batch.draw(laserImage, laser.x, laser.y);
      }
      
      batch.end();

      
      if(Gdx.input.isTouched()) {
         Vector3 touchPos = new Vector3();
         touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
         camera.unproject(touchPos);
         space.x = touchPos.x - 64 / 2;
      }

     
      if(Gdx.input.isKeyPressed(Keys.LEFT)) space.x -= 1000 * Gdx.graphics.getDeltaTime();
      if(Gdx.input.isKeyPressed(Keys.RIGHT)) space.x += 1000 * Gdx.graphics.getDeltaTime();

      
      if(space.x < 0) space.x = 0;
      if(space.x > 800 - 64) space.x = 800 - 64;

      
      if(TimeUtils.nanoTime() - lastDropTime > 300000000) spawnLaser();
      

      
      Iterator<Rectangle> iter = lasers.iterator();
      while(iter.hasNext()) {
         Rectangle laser = iter.next();
         laser.y -= 200 * Gdx.graphics.getDeltaTime();
         if(laser.y + 64 < 0) iter.remove();
         if(laser.overlaps(space)) {
            laserSound.play();
            iter.remove();
         }
      }
      
      
   }

   @Override
   public void dispose() {
      
      bossImage.dispose();
      spaceImage.dispose();
      laserImage.dispose();
      coinImage.dispose();
      laserSound.dispose();
      coinSound.dispose();
      starMusic.dispose();
      batch.dispose();
   }

   @Override
   public void resize(int width, int height) {
   }

   @Override
   public void pause() {
        
   }

   @Override
   public void resume() {
        
   }
}
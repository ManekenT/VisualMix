package main.visualizer;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class PerlinNoiseVisualizer extends Visualizer {

	List<Blob> blobs = new ArrayList<>();
	int[] colors;
	int variation = 1;
	int xScale, yScale, centerX, centerY;

	int lastTime = 0;

	public PerlinNoiseVisualizer(PApplet applet) {
		super(applet);
	}

	@Override
	public void draw() {

		int length = blobs.size();

		_applet.noStroke();
		_applet.fill(0, 0, 0, 5);
		_applet.rect(0, 0, _applet.width, _applet.height);

		int deltaTime = _applet.millis() - lastTime;
		float stepsize = deltaTime * 0.002F;
		for (int i = length - 1; i >= 0; i--) {
			Blob blob = blobs.get(i);

			float x = (float) getSlopeX(blob.x, blob.y);
			float y = (float) getSlopeY(blob.x, blob.y);
			blob.x += blob.direction * x * stepsize;
			blob.y += blob.direction * y * stepsize;

			x = getXPrint(blob.x);
			y = getYPrint(blob.y);
			_applet.stroke(blob.color);
			_applet.strokeWeight(blob.size + 0);
			blob.sizeBonus *= 0.95;
			_applet.line(x, y, blob.lastX, blob.lastY);
			blob.lastX = x;
			blob.lastY = y;

			int border = 200;
			if (x < -border || y < -border || x > _applet.width + border || y > _applet.height + border) {
				blobs.remove(i);
			}
		}
		lastTime = _applet.millis();
	}

	public class Blob {
		public float x;
		public float y;
		public float size;
		public float sizeBonus;
		public float lastX;
		public float lastY;
		public int color;
		public float direction;
	}

	@Override
	public String getName() {
		return "Perlin Noise I";
	}

	@Override
	protected void wholeNote() {
		variation++;
		if (variation > 10) {
			variation = 1;
		}
	}

	@Override
	protected void halfNote() {
		for (Blob b : blobs) {
			b.sizeBonus = 10;
		}
	}

	@Override
	protected void eighthNote() {
		spawnBlob();
		spawnBlob();
		spawnBlob();
		spawnBlob();
		spawnBlob();
		spawnBlob();
	}

	private void spawnBlob() {
		float randomX = _applet.random(0, _applet.width);
		float randomY = _applet.random(0, _applet.height);
		float x = randomX + _applet.random(-100, 100);
		float y = randomY + _applet.random(-100, 100);
		Blob blob = new Blob();
		blob.x = getXPos(x);
		blob.y = getYPos(y);
		blob.size = _applet.random(3, 8);
		blob.lastX = x;
		blob.lastY = y;
		blob.color = colors[PApplet.floor(_applet.random(colors.length))];
		blob.direction = _applet.random(0.1F, 1F) * (_applet.random(1) > 0.5 ? 1 : -1);
		blobs.add(blob);
	}

	private double getSlopeY(double x, double y) {
		switch (variation) {
		case 1:
			return Math.sin(x);
		case 2:
			return Math.sin(x * 5) * y * 0.3;
		case 3:
			return Math.cos(x * y);
		case 4:
			return Math.sin(x) * Math.cos(y);
		case 5:
			return Math.log(Math.abs(x)) * Math.log(Math.abs(y));
		case 6:
			return -Math.sin(x * 0.1) * 3;// orbit
		case 7:
			return (x - x * x * x) * 0.01;// two orbits
		case 8:
			return -Math.sin(x);
		case 9:
			return -y - Math.sin(1.5 * x) + 0.7;
		case 10:
			return Math.sin(x) * Math.cos(y);
		}
		return 0;
	}

	private double getSlopeX(double x, double y) {
		switch (variation) {
		case 1:
			return Math.cos(y);
		case 2:
			return Math.cos(y * 5) * x * 0.3;
		case 3:
		case 4:
		case 5:
		case 6:
			return Math.sin(y * 0.1) * 3;// orbit
		case 7:
			return y / 3;// two orbits
		case 8:
			return -y;
		case 9:
			return -1.5 * y;
		case 10:
			return Math.sin(y) * Math.cos(x);
		}
		return 0;
	}

	private float getXPos(float x) {
		return (x - centerX) / xScale;
	}

	private float getYPos(float y) {
		return (y - centerY) / yScale;
	}

	private float getXPrint(float x) {
		return xScale * x + centerX;
	}

	private float getYPrint(float y) {
		return yScale * y + centerY;
	}

	@Override
	protected void setup() {
		xScale = _applet.width / 20;
		yScale = _applet.height / 20 * (_applet.width / _applet.height);

		centerX = _applet.width / 2;
		centerY = _applet.height / 2;
		colors = new int[] { _applet.color(88, 24, 69), _applet.color(144, 12, 63), _applet.color(199, 0, 57),
				_applet.color(255, 87, 51), _applet.color(255, 195, 15) };
	}
}

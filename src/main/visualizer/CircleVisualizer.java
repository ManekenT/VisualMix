package main.visualizer;

import processing.core.PApplet;
import processing.core.PVector;

public class CircleVisualizer extends Visualizer {

	final int MAX_CIRCLE_CNT = 2500, MIN_CIRCLE_CNT = 100, MAX_VERTEX_CNT = 30, MIN_VERTEX_CNT = 3;

	int circleCnt;
	int vertexCnt = 2;

	public CircleVisualizer(PApplet applet) {
		super(applet);
	}

	@Override
	protected void draw() {
		_applet.background(0);
		_applet.translate(_applet.width / 2, _applet.height / 2);

		updateCntByMouse();

		for (int ci = 0; ci < circleCnt; ci++) {
			float time = (float) (_applet.frameCount) / 20;
			float thetaC = PApplet.map((float) ci, 0F, (float) circleCnt, 0F, (float) Math.PI * 2F);
			float scale = 300;

			PVector circleCenter = getCenterByTheta(thetaC, time, scale);
			float circleSize = getSizeByTheta(thetaC, time, scale);
			int c = getColorByTheta(thetaC, time);

			_applet.stroke(c);
			_applet.noFill();
			_applet.beginShape();
			for (int vi = 0; vi < vertexCnt; vi++) {
				float thetaV = PApplet.map(vi, 0, vertexCnt, 0, (float) Math.PI * 2F);
				float x = circleCenter.x + PApplet.cos(thetaV) * circleSize;
				float y = circleCenter.y + PApplet.sin(thetaV) * circleSize;
				_applet.vertex(x, y);
			}
			_applet.endShape(PApplet.CLOSE);
		}
	}

	@Override
	protected void setup() {

	}

	@Override
	protected String getName() {
		return "Circles";
	}

	void updateCntByMouse() {
		float xoffset = PApplet.abs(_applet.mouseX - _applet.width / 2);
		circleCnt = (int) (PApplet.map(xoffset, 0, _applet.width / 2, MAX_CIRCLE_CNT, MIN_CIRCLE_CNT));
	}

	@Override
	protected void wholeNote() {
		vertexCnt++;
		if (vertexCnt > 10) {
			vertexCnt = 2;
		}
	}

	PVector getCenterByTheta(float theta, float time, float scale) {
		PVector direction = new PVector(PApplet.cos(theta), PApplet.sin(theta));
		float distance = 0.6F + 0.2F * PApplet.cos(theta * 6.0F + PApplet.cos(theta * 8.0F + time));
		PVector circleCenter = PVector.mult(direction, distance * scale);
		return circleCenter;
	}

	float getSizeByTheta(float theta, float time, float scale) {
		float offset = 0.2F + 0.12F * PApplet.cos(theta * 9.0F - time * 2.0F);
		float circleSize = scale * offset;
		return circleSize;
	}

	int getColorByTheta(float theta, float time) {
		float th = 8.0F * theta + time * 2.0F;
		float r = 0.6F + 0.4F * PApplet.cos(th), g = 0.6F + 0.4F * PApplet.cos(th - (float) Math.PI / 3F),
				b = 0.6F + 0.4F * PApplet.cos(th - (float) Math.PI * 2.0F / 3.0F),
				alpha = PApplet.map(circleCnt, MIN_CIRCLE_CNT, MAX_CIRCLE_CNT, 150, 30);
		return _applet.color(r * 255, g * 255, b * 255, alpha);
	}

}

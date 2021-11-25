package env3d.android;

public class Vector3f {
	public float x;
	public float y;
	public float z;
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "Vector: "+x+" "+y+" "+z;
	}
}

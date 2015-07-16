package x;

import android.content.*;
import android.util.*;
import android.widget.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.graphics.drawable.shapes.*;

public class RoundImageView extends ImageView
{
	public RoundImageView(Context c)
	{
		super(c);
	}
	public RoundImageView(Context c, AttributeSet vr)
	{
		super(c, vr);
	}
	public RoundImageView(Context c, AttributeSet vr, int y)
	{
		super(c, vr, y);
	}
	Paint y;



    private void init(){
	  	OvalShape o=new OvalShape();
		ShapeDrawable p=new ShapeDrawable(o);
		p.getPaint().setColor(Color.WHITE);
		this.setBackgroundDrawable(p);
		this.isInEditMode();
		Drawable t=getDrawable();
		if(t==null) return;
		if(getWidth()==0||getHeight()==0) return;
		Bitmap r=((BitmapDrawable)t).getBitmap();
		if(r==null) return;
		Bitmap bm=r.copy(Bitmap.Config.ARGB_8888,true);
		this.setImageBitmap(getBitmap(bm));
		invalidate();
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		// TODO: Implement this method
		super.onSizeChanged(w, h, oldw, oldh);
        init();
	}
  /*
	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
	}
	*/
	int w;
	private Bitmap getBitmap(Bitmap t){
		Bitmap temp;
		w=t.getWidth();
		if(t.getHeight()!=w) temp=Bitmap.createScaledBitmap(t,w,w,false);
		else temp=t;
		Bitmap op=Bitmap.createBitmap(w,w,Bitmap.Config.ARGB_8888);
		Canvas u=new Canvas(op);
		//float vw=this.getMeasuredWidth();
		//Log.i("o","w="+vw);
		Paint p=new Paint();
		Rect r=new Rect(0,0,w,w);
		p.setAntiAlias(true);
		p.setFilterBitmap(true);
		p.setDither(true);
		u.drawARGB(0,0,0,0);
		p.setColor(Color.parseColor("#bab399"));
		u.drawCircle(w/2,w/2,w/2-4f,p);
		p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		u.drawBitmap(temp,r,r,p);
		return op;
	}
	
}

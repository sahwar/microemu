/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@it.pl>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
 
package javax.microedition.lcdui;


public class StringItem extends Item
{

  private StringComponent stringComponent;


  public StringItem(String label, String text)
  {
    super(label);
    stringComponent = new StringComponent(text);
  }


	public StringItem(String label, String text, int appearanceMode)
	{
		this(label, text);
		throw new RuntimeException("TODO");

	}
	

	public int getAppearanceMode()	
	{
		throw new RuntimeException("TODO");
	}


	public Font getFont()
	{
		throw new RuntimeException("TODO");
	}


	public void setFont(Font font)
	{
		throw new RuntimeException("TODO");
	}
		
	
	public String getText()
	{
		return stringComponent.getText();
	}


  public void setLabel(String label)
  {
    super.setLabel(label);
  }


	public void setText(String text)
	{
		stringComponent.setText(text);
	}


	int getHeight()
	{
		return super.getHeight() + stringComponent.getHeight();
	}


  void paint(Graphics g, int w, int h)
  {
		super.paintContent(g);

		g.translate(0, super.getHeight());
		stringComponent.paint(g);
		g.translate(0, -super.getHeight());
  }


	int traverse(int gameKeyCode, int top, int bottom, boolean action)
	{
		Font f = Font.getDefaultFont();

		if (gameKeyCode == Canvas.UP) {
			if (top > 0) {
				if ((top % f.getHeight()) == 0) {
					return -f.getHeight();
				} else {
					return -(top % f.getHeight());
				}
			} else {
				return Item.OUTOFITEM;
			}
		}
		if (gameKeyCode == Canvas.DOWN) {
			if (bottom < getHeight()) {
				if (getHeight() - bottom < f.getHeight()) {
					return getHeight() - bottom;
				} else {
					return f.getHeight();
				}
			} else {
				return Item.OUTOFITEM;
			}
		}

		return 0;
	}

}

#include <stdio.h>
#include <stdlib.h>

#define CMD_LENGTH	32


static int click(int x, int y)
{
	int res, err;
	char cmd[CMD_LENGTH];

	memset(cmd, 0, CMD_LENGTH);
	sprintf(cmd, "input touchscreen tap %d %d", x , y);
	fprintf(stdout,"Cmd is %s\n", cmd); /* DEBUG */
	res = system(cmd);
	fprintf(stdout,"execution returned %d.\n",res);
	/*FIXME handle the return code */
	return res;
}

static int mouse_move(int x, int y)
{
	FILE * pFile;
	pFile = fopen ("/dev/input/mouse0","wb");
	if (pFile!=NULL)
	{
		fputc(8, pFile); /* no click */
		fputc(x, pFile); /* relative X movement */
		fputc(y, pFile); /* relative Y movement */
		fclose (pFile);
	}
	return 0;
}

int main()
{
	printf("Hello World\n");
	mouse_move(5, 5);
	return 0;
}

/** Idea pool:
 *  cat /dev/input/mice | od -t x1 -w3
 *
 * Improved python version:
 * import struct

file = open( "/dev/input/mice", "rb" );

def getMouseEvent():
  buf = file.read(3);
  button = ord( buf[0] );
  bLeft = button & 0x1;
  bMiddle = ( button & 0x4 ) > 0;
  bRight = ( button & 0x2 ) > 0;
  x,y = struct.unpack( "bb", buf[1:] );
  print ("L:%d, M: %d, R: %d, x: %d, y: %d\n" % (bLeft,bMiddle,bRight, x, y) );
  # return stuffs

while( 1 ):
  getMouseEvent();
file.close();

 *
 */

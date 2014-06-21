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

int main()
{
	printf("Hello World\n");
	click(120, 120);
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

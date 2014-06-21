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

NDK=~/opt/adt-bundle-linux-x86_64-20131030/android-ndk-r9d
TOOLCHAIN_ROOT=$(NDK)/toolchains/arm-linux-androideabi-4.8/prebuilt/linux-x86_64
CC=$(TOOLCHAIN_ROOT)/bin/arm-linux-androideabi-gcc
# Android includes
ANR_INC=$(TOOLCHAIN_ROOT)/include

#CFLAGS=-static
CFLAGS=-I $(ANR_INC)

APP=moused
SRC=main.c

$(APP) : $(SRC)
	$(CC) -o $(APP) $(CFLAGS) $(SRC)

clean:
	rm -rf $(APP)

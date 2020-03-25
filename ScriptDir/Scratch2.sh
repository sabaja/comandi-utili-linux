#Install adobe air as state on https://www.noobslab.com/2015/05/adobeair-is-now-available-for-ubuntu.html

wget -O adobe-air_amd64.deb https://drive.noobslab.com/data/apps/AdobeAir/adobeair_2.6.0.2_amd64.deb
sudo dpkg -i adobe-air_amd64.deb
sudo apt install -f

#Install pixbuf stuff

sudo apt install gtk2-engines-pixbuf:i386

# install necesary i386 libraries
sudo apt install libgtk2.0-0:i386 libstdc++6:i386 libxml2:i386 libxslt1.1:i386 libcanberra-gtk-module:i386 gtk2-engines-murrine:i386 libqt4-qt3support:i386 libgnome-keyring0:i386 libnss-mdns:i386 libnss3:i386

# make keyring visible for Adobe Air
sudo ln -s /usr/lib/i386-linux-gnu/libgnome-keyring.so.0 /usr/lib/libgnome-keyring.so.0
sudo ln -s /usr/lib/i386-linux-gnu/libgnome-keyring.so.0.2.0 /usr/lib/libgnome-keyring.so.0.2.0

# Download Adobe Air
cd ~/Downloads
wget https://airdownload.adobe.com/air/lin/download/2.6/AdobeAIRSDK.tbz2
sudo mkdir /opt/adobe-air-sdk
sudo tar jxf AdobeAIRSDK.tbz2 -C /opt/adobe-air-sdk

# Download Air runtime/SDK from Archlinux
wget https://aur.archlinux.org/cgit/aur.git/snapshot/adobe-air.tar.gz
sudo tar xvf adobe-air.tar.gz -C /opt/adobe-air-sdk
sudo chmod +x /opt/adobe-air-sdk/adobe-air/adobe-air

# Get actual scratch file URL from https://scratch.mit.edu/scratch2download/

sudo mkdir /opt/adobe-air-sdk/scratch
wget https://scratch.mit.edu/scratchr2/static/sa/Scratch-456.0.1.air
sudo cp Scratch-456.0.1.air /opt/adobe-air-sdk/scratch/
cp Scratch-456.0.1.air /tmp/
cd /tmp/
unzip /tmp/Scratch-456.0.1.air
sudo cp /tmp/icons/AppIcon128.png /opt/adobe-air-sdk/scratch/scratch.png

# Use the following command to launch Scratch2

/opt/adobe-air-sdk/adobe-air ./Scratch-456.0.1.air

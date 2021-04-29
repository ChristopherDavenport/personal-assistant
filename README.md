# Personal Assistant

Currently tied to Ubuntu, will likely work more widely as well eventually. Make sure to get a model
for the `asr` component. There is a script available in `asr/get-model.sh` which will get a minimal model.

```sh
# Installation for ASR
sudo apt-get install libasound-dev portaudio19-dev libportaudio2 libportaudiocpp0 
sudo apt-get ffmpeg libav-tools
sudo pip3 install vosk sounddevice pyaudio
# Installation for Command Processor
# Install Coursier For Running
curl -fLo cs https://git.io/coursier-cli-"$(uname | tr LD ld)"
chmod +x cs
./cs install cs
rm cs
cs install sbt-launcher
cs install --contrib catscript

# Text To Speech Component
sudo apt-get install mbrola-us1 espeak-ng
```
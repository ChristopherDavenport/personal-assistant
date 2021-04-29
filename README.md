# Personal Assistant

Personal Assistant is an application whose job is to engage your microphone and then engages a command system.
Currently it supports the `say` command which will echo out what you tell it to say. We will enhance it with
additional capabilities as we go.

## Installation

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


# Run The Personal Assistant
./run.catscript
```

### Licenses

I'm trying to track down what the license requirements are across all of these deps. I'm not as familiar with python and server installed components licensing and so if you have insights that can help me do an appropriate understanding of licensing requirements the help would be very appreciated. If you believe I am in error in my terms please let me know.
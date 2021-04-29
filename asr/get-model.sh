#!/usr/bin/env bash

# Large Model 1.4G
#MODEL="vosk-model-en-us-aspire-0.2"
# Small Model 40MB - Seems to work ok, not perfect
MODEL="vosk-model-small-en-us-0.15"

rm -r $MODEL
wget -c "https://alphacephei.com/vosk/models/$MODEL.zip"
unzip $MODEL.zip
mv $MODEL model 
rm $MODEL.zip

# ln -s ../../../python/vosk-model-en-us-aspire-0.2 model
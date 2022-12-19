#!/bin/sh

# Change this to where the plugin should be copied to
plugin_path="$HOME/desk/mc_servers/plugin_test/plugins/"

# Compile
mvn clean package "$@"

# Remove old
rm -f "$plugin_path"AlchimiaVitae*.jar

# Copy new
cp ./target/AlchimiaVitae-vUNOFFICIAL.jar "$plugin_path"

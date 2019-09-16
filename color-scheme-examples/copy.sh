#!/bin/bash

# copy site resource images from color-scheme module
cp ../color-scheme/src/site/resources/images/*.svg src/main/resources/org/dishevelled/color/scheme/examples/

# create temporary destination
mkdir -p new/src/main/resources/org/dishevelled/color/scheme/examples/

# remove text and tspan elements
for i in `find src -name *.svg`; do cat $i | grep -v text | grep -v tspan | sed 's/height="80"/height="60"/g'> new/$i; done

# overrite old resources
mv new/src/main/resources/org/dishevelled/color/scheme/examples/*.svg src/main/resources/org/dishevelled/color/scheme/examples/

# cleanup
rm -Rf new

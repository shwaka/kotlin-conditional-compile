#! /bin/bash

for proj in kococo-debug kococo-release; do
    (
        cd $proj
        ./gradlew bintrayUpload
    )
done

#!/bin/bash
velocity.sh --properties "lower0=first,lower1=second,lower2=third,Upper0=First,Upper1=Second,Upper2=Third,UPPER0=FIRST,UPPER1=SECOND,UPPER2=THIRD" --template tertiaryNode.wm
velocity.sh --properties "lower0=first,lower1=second,lower2=fourth,Upper0=First,Upper1=Second,Upper2=Fourth,UPPER0=FIRST,UPPER1=SECOND,UPPER2=FOURTH" --template tertiaryNode.wm
velocity.sh --properties "lower0=first,lower1=third,lower2=fourth,Upper0=First,Upper1=Third,Upper2=Fourth,UPPER0=FIRST,UPPER1=THIRD,UPPER2=FOURTH" --template tertiaryNode.wm
velocity.sh --properties "lower0=second,lower1=third,lower2=fourth,Upper0=Second,Upper1=Third,Upper2=Fourth,UPPER0=SECOND,UPPER1=THIRD,UPPER2=FOURTH" --template tertiaryNode.wm
velocity.sh --properties "lower0=first,lower1=second,Upper0=First,Upper1=Second,UPPER0=FIRST,UPPER1=SECOND" --template binaryNode.wm
velocity.sh --properties "lower0=first,lower1=third,Upper0=First,Upper1=Third,UPPER0=FIRST,UPPER1=THIRD" --template binaryNode.wm
velocity.sh --properties "lower0=first,lower1=fourth,Upper0=First,Upper1=Fourth,UPPER0=FIRST,UPPER1=FOURTH" --template binaryNode.wm
velocity.sh --properties "lower0=second,lower1=third,Upper0=Second,Upper1=Third,UPPER0=SECOND,UPPER1=THIRD" --template binaryNode.wm
velocity.sh --properties "lower0=second,lower1=fourth,Upper0=Second,Upper1=Fourth,UPPER0=SECOND,UPPER1=FOURTH" --template binaryNode.wm
velocity.sh --properties "lower0=third,lower1=fourth,Upper0=Third,Upper1=Fourth,UPPER0=THIRD,UPPER1=FOURTH" --template binaryNode.wm

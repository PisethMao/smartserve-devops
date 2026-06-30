#!/bin/bash
message="$1"
if [ -z "$message" ]; then
    echo "Usage: Bash $0 <commit-message>"
    exit 1
fi
git add .
git status
git commit -m "$message"
git push -u origin $(git branch --show-current)
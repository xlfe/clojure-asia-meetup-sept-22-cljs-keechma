#!/bin/bash

rm -rf public/js
TAOENSSO_TIMBRE_MIN_LEVEL_EDN=:warn npx shadow-cljs release app

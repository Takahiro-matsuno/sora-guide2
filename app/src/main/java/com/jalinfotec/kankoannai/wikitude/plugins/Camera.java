package com.jalinfotec.kankoannai.wikitude.plugins;

interface Camera {

    void start();
    void stop();

    int getCameraOrientation();
}

<?php

/*
CREATE TABLE acc (
    uuid VARCHAR(255) NOT NULL,
    id INT NOT NULL,
    time INT NOT NULL,
    accX FLOAT NOT NULL,
    accY FLOAT NOT NULL,
    accZ FLOAT NOT NULL
);

CREATE TABLE gyro (
    uuid VARCHAR(255) NOT NULL,
    id INT NOT NULL,
    time INT NOT NULL,
    gyroX FLOAT NOT NULL,
    gyroY FLOAT NOT NULL,
    gyroZ FLOAT NOT NULL
);

CREATE TABLE ori (
    uuid VARCHAR(255) NOT NULL,
    id INT NOT NULL,
    time INT NOT NULL,
    oriX FLOAT NOT NULL,
    oriY FLOAT NOT NULL,
    oriZ FLOAT NOT NULL,
    oriW FLOAT NOT NULL
);

CREATE TABLE touch (
    uuid VARCHAR(255) NOT NULL,
    id INT NOT NULL,
    time INT NOT NULL,
    fingerID INT NOT NULL,
    type VARCHAR(10) NOT NULL,
    touchX FLOAT NOT NULL,
    touchY FLOAT NOT NULL,
    pressure FLOAT NOT NULL
);

CREATE TABLE activity (
    uuid VARCHAR(255) NOT NULL,
    id INT NOT NULL,
    time INT NOT NULL,
    type INT NOT NULL
);
 */

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

header('Content-Type: text/plain; charset=UTF-8');

if($_SERVER['REQUEST_METHOD'] !== 'POST') {
    die('error (1)');
}

/* $version = isset($_GET['v']) ? (int) $_GET['v'] : 0;
 * if($version < 1) {
 *     die('error (2)');
 * }
 */

$body = file_get_contents('php://input');
if(empty($body)) {
    die('error (3)');
}

$db = new mysqli('localhost', 'logger', 'gHquDXFP2ok38poxZscWPEfso', 'imu');
$db->set_charset('utf8mb4');
if($db->connect_error) {
    die('error (4)');
}

$acc_stmt = $db->prepare('INSERT INTO acc (uuid, id, time, accX, accY, accZ) VALUES (?, ?, ?, ?, ?, ?)');
if(!$acc_stmt) {die('error (acc)');}

$activity_stmt = $db->prepare('INSERT INTO activity (uuid, id, time, type) VALUES (?, ?, ?, ?)');
if(!$activity_stmt) {die('error (activity)');}

$gyro_stmt = $db->prepare('INSERT INTO gyro (uuid, id, time, gyroX, gyroY, gyroZ) VALUES (?, ?, ?, ?, ?, ?)');
if(!$gyro_stmt) {die('error (gyro)');}

$ori_stmt = $db->prepare('INSERT INTO ori (uuid, id, time, oriX, oriY, oriZ, oriW) VALUES (?, ?, ?, ?, ?, ?, ?)');
if(!$ori_stmt) {die('error (ori)');}

$touch_stmt = $db->prepare('INSERT INTO touch (uuid, id, time, fingerID, type, touchX, touchY, pressure) VALUES (?, ?, ?, ?, ?, ?, ?, ?)');
if(!$touch_stmt) {die('error (touch)');}

$lines = trim($body);
$json = json_decode($lines, true);

$uuid = $json['uuid'];

$accEntities = $json['accEntities'];
$gyroEntities = $json['gyroEntities'];
$oriEntities = $json['oriEntities'];
$touchEntities = $json['touchEntities'];
$activityEntities = $json['activityEntities'];

foreach($accEntities as $item) {
    if($item === null) {continue;}
    $acc_stmt->bind_param("siiddd", $uuid, $item['id'], $item['time'], $item['accX'], $item['accY'], $item['accZ']);
    $acc_stmt->execute();
}
$acc_stmt->close();


foreach($gyroEntities as $item) {
    if($item === null) {continue;}
    $gyro_stmt->bind_param("siiddd", $uuid, $item['id'], $item['time'], $item['gyroX'], $item['gyroY'], $item['gyroZ']);
    $gyro_stmt->execute();
}
$gyro_stmt->close();

foreach($oriEntities as $item) {
    if($item === null) {continue;}
    $ori_stmt->bind_param("siidddd", $uuid, $item['id'], $item['time'], $item['oriX'], $item['oriY'], $item['oriZ'], $item['oriW']);
    $ori_stmt->execute();
}
$ori_stmt->close();

foreach($touchEntities as $item) {
    if($item === null) {continue;}
    $touch_stmt->bind_param("siiisiid", $uuid, $item['id'], $item['time'], $item['fingerID'], $item['type'], $item['touchX'], $item['touchY'], $item['pressure']);
    $touch_stmt->execute();
}
$touch_stmt->close();

foreach($activityEntities as $item) {
    if($item === null) {continue;}
    $activity_stmt->bind_param("siis", $uuid, $item['id'], $item['time'], $item['type']);
    $activity_stmt->execute();
}
$activity_stmt->close();

$db->close();

echo 'ok';
?>
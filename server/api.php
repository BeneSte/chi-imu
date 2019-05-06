<?php

/*
CREATE TABLE acc (
    uuid VARCHAR(255) NOT NULL,
    id INT NOT NULL,
    time INT NOT NULL,
    accX FLOAT NOT NULL,
    accY FLOAT NOT NULL,
    axxZ FLOAT NOT NULL
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
if(!$acc_stmt) {
    die('error (acc)');
}

$activity_stmt = $db->prepare('INSERT INTO activity (uuid, id, time, type) VALUES (?, ?, ?, ?)');
if(!$activity_stmt) {
    die('error (activity)');
}

$gyro_stmt = $db->prepare('INSERT INTO gyro (uuid, id, time, gyroX, gyroY, gyroZ) VALUES (?, ?, ?, ?, ?, ?)');
if(!$gyro_stmt) {
    die('error (gyro)');
}

$ori_stmt = $db->prepare('INSERT INTO ori (uuid, id, time, oriX, oriY, oriZ, oriW) VALUES (?, ?, ?, ?, ?, ?, ?)');
if(!$ori_stmt) {
    die('error (ori)');
}

$touch_stmt = $db->prepare('INSERT INTO touch (uuid, id, time, fingerID, type, touchX, touchY, pressure) VALUES (?, ?, ?, ?, ?, ?, ?, ?)');
if(!$touch_stmt) {
    die('error (touch)');
}

$lines = explode("\n", trim($body));
echo $lines;

for($i = 0; $i < count($lines); $i++) {
    $line = $lines[$i];
    list($local_id, $local_time, $content) = explode("\t", $line, 3);

    $json = json_decode($content, true);
    if($json === null) {
        continue;
    }

    $uuid = $json['uuid'];
    $acc_stmt->bind_param('isiis', $version, $uuid, $local_id, $local_time, $content);
    $acc_stmt->execute();
}

$acc_stmt->close();
$db->close();

echo 'ok';

?>

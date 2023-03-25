#!/usr/bin/env bash


# остановить предыдущую версию приложения, если она была запущена
systemctl stop my-app.service

# скачать новую версию приложения из Git-репозитория
git clone https://github.com/JAfar133/Booking.git
cd Booking

chmod +x mvnw
./mvnw clean package

# скопировать файл jar в каталог с системными службами
cp target/bookingSite-0.0.1-SNAPSHOT.jar.jar /usr/local/lib/bookingSite-0.0.1-SNAPSHOT.jar.jar

echo "[Unit]
Description=Booking site
After=syslog.target

[Service]
User=root
ExecStart=/usr/bin/java -jar /usr/local/lib/bookingSite-0.0.1-SNAPSHOT.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target" > /etc/systemd/system/bookingSite-0.0.1-SNAPSHOT.service

# перезапустить систему служб, чтобы применить изменения
systemctl daemon-reload

# запустить приложение в качестве службы systemd
systemctl start my-app.service
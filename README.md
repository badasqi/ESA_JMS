# LR4 JMS
В качестве броккера был использован RabbitMQ 3.12.2, описан конфигурационный файл [RabbitMQ](https://github.com/badasqi/ESA_JMS/blob/main/src/main/java/com/example/esalab3/configRabbit/RabbitMQConfig.java)

Были добавлены 2 таблицы в БД - ChangeLog (строка - инфа об изменении), Notification (строка - почты кому рассылаем уведомление)

Реализованы в /messaging Consumer (слушает сообщения из RabbitMQ, сохраняет изменения в таблицу ChangeLog)

MailConsumer(слушает сообщения из RabbitMQ, вызывает метод сервиса и отправляет SimpleMailMessage на адреса из таблицы Notification), Producer(отправляет сообщения в RabbitMQ)

## Пример сообщения
В теле письма переобределённый toString() для ChangeLog.
![image](https://github.com/badasqi/ESA_JMS/assets/78803025/deb8be86-4aa7-4035-b64e-b3ac7873fda0)


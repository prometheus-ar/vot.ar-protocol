# vot.ar-protocol

Protocolo de escritura y lectura de boletas VOT.AR

Para ver un ejemplo de cómo escribir y leer un voto, [revisar este
test](src/test/java/com/github/prometheus/votar/protocol/ProtocolTest.java).

El test escribe un voto con datos reales, y lee [el dump de un
voto](src/test/resources/original-vote.bin) emitido por las máquinas de VOT.AR.

El protocolo está explicado en detalle [en estas especificaciones](
http://justpaste.it/MSAapk).

El dump de un voto se ve más o menos así:

```
1c01 001d df85 8e8e 3036 4341 4241 2e31
434f 4d35 3533 3344 4950 3532 3937 4a45
4635 3239 3200 0000 0000 0000 0000 0000
0000 0000 0000 0000 0000 0000 0000 0000
0000 0000 0000 0000 0000 0000 0000 0000
0000 0000 0000 0000 0000 0000 0000 0000
0000 0000 0000 0000 0000 0000 575f 4f4b
```


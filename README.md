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

El formato de los datos de un voto (que comienzan en el offset 0x8) es el
siguiente:

[longitud-distrito][distrito][tipo-candidato-N][id-candidato-N]

Donde:

* **longitud-distrito**. 2 bytes. Es la longitud del campo que sigue en formato
String, y con un padding de "0" a la izquierda en caso de ser menor a 10.

* **distrito**. Longitud variable. Nombre del distrito. Un distrito es la
ubicación marcada como clase "Comuna" en el archivo de datos
[Ubicaciones_1.json](src/test/resources/data/Ubicaciones_1.json).

* **tipo-candidato-N**. 3 bytes. Es el campo cod_categoria definido para cada
candidato en el archivo de datos [Candidatos.json](
src/test/resources/data/CABA.1/Candidatos.json).

* **id-candidato-N**. 4 bytes. Identificador del candidato. Es la parte del
campo ```codigo``` que se encuentra después del punto separador de lista. De
acuerdo al siguiente candidato:

```
  {
    "asistida": null, 
    "cod_categoria": "COM", 
    "cod_lista": "16", 
    "codigo": "16.5533", 
    "nombre": "Clori Yelicic", 
    "numero_de_orden": 2, 
    "sexo": "F", 
    "titular": true
  }
```

el identificador es "5533". A este campo se le agrega un padding de 0s a la
izquierda.

Ejemplo de voto:

```
06CABA.1COM5533DIP5297JEF5292
```

Como se observa en el ejemplo, cada voto tiene tres candidatos, uno para
comunero (COM), otro para legislador (DIP), y otro para jefe de gobierno (JEF).

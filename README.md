﻿## Управление и описание
На экран выводится оси координат произвольная функция, реализованная через стороннюю библиотеку-парсер мат. формул.  
С помощью мыши можно упарвлять параметрами обзора(камеры).  
При перемещении курсора с зажатой левой кнопкой мыши происходит изменения угла обзора.  
При перемещении курсора с зажатой правой кнопкой мыши происходит изменение положения камеры (камера смещается по осям X и Y).  
При перемещении курсора с зажатой средней кнопкой мыши происходит изменение положение камеры вдоль оси Z (расстояние вычисляется по изменению в плоскости XY, а направление - по направлению мыши: вверх - увеличиваем Z, вниз - уменьшаем Z). Т.к. сейчас нет сокрытия объектов, находящихся за камерой, то эффект от перемещения заметен только если изменены параметры центральной проекции.
При прокручивании колёсика изменяется масштаб.  
При прокручивании колёсика с зажатой кнопкой Control происходит изменение параметров центральной проекции.  

Можно начать или прекратить вращение функции вокруг определенной оси. Пока реализовано вращение только вокруг одной оси
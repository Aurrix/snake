package snake.app.point;
/**
 * Copyright 2002-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Alisher Urunov
 */
public class Point extends java.awt.Point implements Comparable {
    public Point(int x,int y){
        this.x=x;
        this.y=y;
    }
    @Override
    public int compareTo(Object o) {
        Point compareObject;
        if (o instanceof java.awt.Point) compareObject =  (Point)o;
        else{return 0;}
        if (this.x == compareObject.x && this.y == compareObject.y) return 1;
        return 0;
    }
}

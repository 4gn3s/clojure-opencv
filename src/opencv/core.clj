(ns opencv.core
  (:require
   [opencv.opencv :as cv]
   [opencv.viewer :as viewer]
  )
  (:import
    org.opencv.core.Core
    org.opencv.highgui.Highgui))


(defn load-image
  [filename]
  (let [image (Highgui/imread (.toString filename))]
    (if (.empty image)
    (println "Failed to load image.") ;if
    (println "Image loaded.")) ;else
  image))

(defn save-image
  [image filename]
  (if-not (Highgui/imwrite (.toString filename) image)
  (println "Unable to save image to " filename)))
  ;(println "Image saved."))

(defn process-and-save-image!
  []
  (let [image (load-image "resources/lena.png")]
    ;(save-image (threshold (gaussian-blur image 5 3) 80 255) "output.png")
    ;(viewer/imshow (cv/canny (cv/gaussian-blur image 5 3) 30 90))
    (viewer/imshow (cv/find-draw-largest-contour (cv/threshold (cv/blur (cv/grayscale image) 3) 127 255)))
    ;(println "")
   ))

(defn -main
  []
  (clojure.lang.RT/loadLibrary Core/NATIVE_LIBRARY_NAME)
  (process-and-save-image!))


(-main)

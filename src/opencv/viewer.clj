(ns opencv.viewer
  (:import
   org.opencv.core.Core
   org.opencv.core.Mat
   org.opencv.imgproc.Imgproc)
  (:require
   [opencv.opencv :as cv]
  ))


(defn to-buffered-image [mat]
  (let [out (Mat.)
        colour? (< 1 (.channels mat))
        type (if colour?
               java.awt.image.BufferedImage/TYPE_3BYTE_BGR
               java.awt.image.BufferedImage/TYPE_BYTE_GRAY)
        width (.cols mat)
        height (.rows mat)]
    (do
      (if colour?
        (Imgproc/cvtColor mat out Imgproc/COLOR_BGR2RGB)
        (.copyTo mat out))
      (let [blen (* (.channels mat) width height)
            bytes (byte-array blen)]
        (.get out 0 0 bytes)
        (let [image (java.awt.image.BufferedImage. width height type)
              raster (.getRaster image)]
          (.setDataElements raster 0 0 width height bytes)
          image)))))

(defn show-frame
  [image title]
  (doto (javax.swing.JFrame.)
    (.setTitle title)
    (.add (proxy [javax.swing.JPanel] []
            (paint [g] (.drawImage g image 0 0 nil))))
    (.setSize (java.awt.Dimension. (.getWidth image) (.getHeight image)))
    (.show)))

(defn imshow
  ([mat]
     (imshow mat "Untitled image"))
  ([mat title]
     (show-frame (to-buffered-image mat) title)))

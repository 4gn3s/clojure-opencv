(ns opencv.opencv
  (:import
    org.opencv.core.Core
    org.opencv.core.Mat
    org.opencv.core.MatOfRect
    org.opencv.core.Point
    org.opencv.core.Rect
    org.opencv.core.Scalar
    org.opencv.core.Size
    org.opencv.highgui.Highgui
    org.opencv.imgproc.Imgproc))

(clojure.lang.RT/loadLibrary Core/NATIVE_LIBRARY_NAME)

(defn clone
  [mat]
  (.clone mat))

(defn grayscale
  [image]
  (let [dest (clone image)]
    (Imgproc/cvtColor image dest Imgproc/COLOR_BGR2GRAY)
  dest))

(defn blur
  [image size]
  (let [blurred (clone image)]
     (Imgproc/blur image blurred (Size. size size) (Point. -1 -1) Imgproc/BORDER_DEFAULT)
    blurred))

(defn gaussian-blur
  [image square-size sigma]
  (let [dest (clone image)]
    (print "Blurring...")
    (Imgproc/GaussianBlur image dest (Size. square-size square-size) sigma sigma)
    (println " done!")
    dest))

(defn threshold
  [image threshold max-threshold]
  (let [dest (clone image)]
    (print "Thresholding...")
    (Imgproc/threshold image dest threshold max-threshold Imgproc/THRESH_BINARY)
    (println " done!")
    dest))

(defn canny
  [image threshold-low threshold-high]
  (let [dest (clone image)]
    (Imgproc/Canny image dest threshold-low threshold-high)
    dest))

(defn find-contours
  [image]
  (let [contours (java.util.LinkedList.)]
    (Imgproc/findContours image contours (Mat.) Imgproc/RETR_LIST Imgproc/CHAIN_APPROX_SIMPLE)
    (println "Contours size: " (.size contours))
    contours))

(defn find-contours-external
  [image]
  (let [contours (java.util.LinkedList.)]
    (Imgproc/findContours image contours (Mat.) Imgproc/RETR_EXTERNAL Imgproc/CHAIN_APPROX_SIMPLE)
    (println "Contours size: " (.size contours))
    contours))

(defn largest-contour
  [image]
  (->> (find-contours image)
       (sort-by #(- (Imgproc/contourArea %)))
       first))

(defn draw-contours!
  [image contour-list color]
  (let [dest (clone image)]
    (Imgproc/drawContours dest contour-list -1 color 2) ;doesn't work?
    dest))

(defn find-draw-contours
  [image]
  (let [dest (clone image)]
    (draw-contours! image (find-contours-external dest) (Scalar. 0 255 0))
    image))

(defn find-draw-largest-contour
  [image]
  (let [dest (clone image)]
    draw-contours! image (largest-contour dest) (Scalar. 255 0 0)
    image))

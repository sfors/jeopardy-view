(ns ^:figwheel-hooks jeopardy.view.main
  (:require
    [goog.dom :as gdom]
    [reagent.dom :as rdom]
    [re-frame.core :as re-frame]
    [jeopardy.view.events :as events]
    [jeopardy.view.subs :as subs]
    [jeopardy.view.components.service-button :refer [call-service]]))

;; define your app data so that it doesn't get over-written on reload
(defn get-app-element []
  (gdom/getElement "app"))

(defn flip-card
[value question flipped]
  [:td {:style { :color "#DDD" }}
   [:div {:style { :background-color "transparent" :perspective "1000px" :width "100%" :height "49px"}}
    [:div {:style { :width "100%" :height "100%" :position "relative" :transition "transform 0.8s" :transform-style "preserve-3d" :transform (if flipped "rotateY(180deg)" "")}}
     [:div {:style {:border-radius "3px" :background "linear-gradient(145deg, rgba(100,100,200,1) 0%, rgba(0,0,200,1) 100%)" :position "absolute" :width "100%" :height "100%" :backface-visibility "hidden"}} value]
     [:div {:style {:border-radius "3px" :background "linear-gradient(145deg, rgba(100,100,200,1) 0%, rgba(0,0,200,1) 100%)" :position "absolute" :width "100%" :height "100%" :backface-visibility "hidden" :transform "rotateY(180deg)"}} question]
     ]]])

(defn app-component
  []
  (let [clicks (deref (re-frame/subscribe [::subs/clicks]))
        flipped (= (mod clicks 2) 1)
        tdStyle {:border-radius "3px" :padding "1rem" :color "#DDD" :background "linear-gradient(145deg, rgba(100,100,200,1) 0%, rgba(0,0,200,1) 100%)"}]
    [:div
     [:h1 "Jeopardy"]
     [:table {:style {:border-radius "5px" :background-color "black" :text-align "center"} :cellspacing "3px"}
      [:tr [:th {:style tdStyle} "category1"] [:th {:style tdStyle} "category2"] [:th {:style tdStyle} "category3"] [:th {:style tdStyle} "category4"] [:th {:style tdStyle} "category5"] [:th {:style tdStyle} "category6"]]
      [:tr [flip-card "200" "Frågan" flipped] [flip-card "200" "Frågan" flipped] [flip-card "200" "Frågan" flipped] [flip-card "200" "Frågan" flipped] [flip-card "200" "Frågan" flipped] [flip-card "200" "Frågan" flipped]]
      [:tr [flip-card "400" "Frågan" flipped] [flip-card "400" "Frågan" flipped] [flip-card "400" "Frågan" flipped] [flip-card "400" "Frågan" flipped] [flip-card "400" "Frågan" flipped] [flip-card "400" "Frågan" flipped]]
      [:tr [flip-card "600" "Frågan" flipped] [flip-card "600" "Frågan" flipped] [flip-card "600" "Frågan" flipped] [flip-card "600" "Frågan" flipped] [flip-card "600" "Frågan" flipped] [flip-card "600" "Frågan" flipped]]
      [:tr [flip-card "800" "Frågan" flipped] [flip-card "800" "Frågan" flipped] [flip-card "800" "Frågan" flipped] [flip-card "800" "Frågan" flipped] [flip-card "800" "Frågan" flipped] [flip-card "800" "Frågan" flipped]]
      [:tr [flip-card "1000" "Frågan" flipped] [flip-card "1000" "Frågan" flipped] [flip-card "1000" "Frågan" flipped] [flip-card "1000" "Frågan" flipped] [flip-card "1000" "Frågan" flipped] [flip-card "1000" "Frågan" flipped]]
     ]
    [:p {:style {:color "red"}} (str "Clicks: " clicks)]
    [:button
     {:on-click (fn []
                  (re-frame/dispatch [::events/button-clicked]))}
     "Click me!"]
     [call-service]]))


(defn mount [el]
  (rdom/render [app-component] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn on-reload
  {:after-load true}
  []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )


(defn init []
  (re-frame/dispatch [::events/initialize-db]))

(when (= (deref re-frame.db/app-db) {})
  (init))

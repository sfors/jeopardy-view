(ns jeopardy.view.events
  (:require
    [re-frame.core :as re-frame]
    [day8.re-frame.http-fx]
    [ajax.core :as ajax]
    [jeopardy.view.core :as core]
    [clojure.edn :refer [read-string]]))


(re-frame/reg-event-db
  ::initialize-db
  (fn [_]
    {:clicks 0}))


(re-frame/reg-event-db
  ::button-clicked
  (fn [db] (update db :clicks inc)))


(re-frame/reg-event-fx
  ::join-game
  (fn [{db :db} _]
    {:websocket-connect {:username (:username db)}}))


(re-frame/reg-event-fx
  ::call-service
  (fn [_ _]
    {:fx [[:http-xhrio
           {:method     :get
            :uri        "http://localhost:7000/"
            :response-format
                        (ajax/json-response-format {:keywords? true})
            :on-success [::response-received]
            :on-failure [::service-call-failed]
            }]]}))

(re-frame/reg-event-db
  ::response-received
  (fn [db [_ response]]
    (println response)
    (assoc db :service-response response)))

(re-frame/reg-event-db
  ::username-changed
  (fn [db [_ value]]
    (assoc db :username value)))

(re-frame/reg-event-db
  ::on-server-message
  (fn [db [_ response]]
    (println "Got a websocket message:" response)))


(defonce websocket-atom (atom nil))

(re-frame/reg-fx
  :websocket-connect
  (fn [{username :username}]
    (let [ws (js/WebSocket. "ws://localhost:7000")]
      (set! (.-onmessage ws)
            (fn [e]
              (let [response (read-string (.-data e))]
                (re-frame/dispatch [::on-server-message response]))))

      (set! (.-onclose ws)
            (fn [e]
              (println "Websocket closed")
              (reset! websocket-atom nil)
              ))

      (set! (.-onopen ws)
            (fn [e]
              (println "On open websocket!")
              ;(swap! state-atom core/set-connection :connected)
              (.send (deref websocket-atom)
                     (str {:username username}))))

      (reset! websocket-atom ws)
      )))

(re-frame/reg-event-fx
  ::call-get-board
  (fn [_ _]
    {:fx [[:http-xhrio
           {:method :get
            :uri "http://localhost:7000/get-board"
            :response-format
                    (ajax/json-response-format {:keywords? true})
            :on-success [::get-board-received]
            :on-failure [::service-call-failed]
            }]]}))

(re-frame/reg-event-db
  ::get-board-received
  (fn [db [_ response]]
    (println response)
    (core/receive-get-board db response)))

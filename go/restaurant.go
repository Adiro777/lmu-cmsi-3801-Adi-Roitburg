package main

import (
	"log"
	"math/rand"
	"sync"
	"sync/atomic"
	"time"
)

func do(seconds int, action ...any) {
	log.Println(action...)
	randomMillis := 500*seconds + rand.Intn(500*seconds)
	time.Sleep(time.Duration(randomMillis) * time.Millisecond)
}

type Order struct {
	id       uint64
	customer string
	reply    chan *Order
	cook     string
}

var nextID atomic.Uint64
var Waiter = make(chan *Order, 3)

func main() {
	customers := [10]string{
		"Ani", "Bai", "Cat", "Dao", "Eve", "Fay", "Gus", "Hua", "Iza", "Jai",
	}
	var wg sync.WaitGroup
	for _, customer := range customers {
		wg.Add(1)
		go Customer(customer, &wg)
	}

	go Cook("Remy")
	go Cook("Colette")
	go Cook("Linguini")

	wg.Wait()
	log.Println("Restaurant is closing!")
}

func Cook(name string) {
	log.Println(name, " is cooking!")
	for {
		order := <-Waiter
		do(10, name, "is preparing order", order.id)
		order.cook = name
		order.reply <- order
	}
}

func Customer(name string, wg *sync.WaitGroup) {
	defer wg.Done()

	for mealsEaten := 0; mealsEaten < 5; {
		order := &Order{
			id:       nextID.Add(1),
			customer: name,
			reply:    make(chan *Order),
		}
		log.Println(name, "placed order", order.id)
		select {
		case Waiter <- order:
			meal := <-order.reply
			do(2, name, "eating cooked order", meal.id, "prepared by", meal.cook)
			mealsEaten += 1

		case <-time.After(7 * time.Second):
			do(5, name, "waiting too long, abandoning order", order.id)
		}
	}
	log.Println(name, "going home")
}

#include <atomic>
#include <condition_variable>
#include <cstdlib>
#include <deque>
#include <functional>
#include <limits>
#include <mutex>
#include <thread>
#include <vector>

template <typename T>
struct concurrent_queue
{
    typedef std::unique_lock<std::mutex> mutex_lock;

    void push(T const& val)
    {
        mutex_lock lock(m);
        q.push_back(val);
        lock.unlock();
        cond_var.notify_one();
    }

    bool empty() const
    {
        mutex_lock lock(m);
        return q.empty();
    }

    bool try_pop(T& popped_value)
    {
        mutex_lock lock(m);
        if (q.empty())
            return false;

        popped_value = q.front();
        q.pop_front();
        return true;
    }

    void wait_and_pop(T& popped_value)
    {
        mutex_lock lock(m);
        while (q.empty())
            cond_var.wait(lock);

        popped_value = q.front();
        q.pop_front();
    }

    size_t size() const
    {
        std::unique_lock<std::mutex> lock(m);
        return q.size();
    }

private:
    size_t                  max_size;
    std::deque<T>           q;
    mutable std::mutex      m;
    std::condition_variable cond_var;
};

void producer(std::atomic<bool>& keep_working, concurrent_queue<std::vector<char> >& sink)
{
    while (keep_working.load())
    {
        if (sink.size() > 10000)
            std::this_thread::yield();
        else
            sink.push(std::vector<char>(rand() % 10000));
    }
}

int main(int argc, char *argv[])
{
    std::atomic<bool> keep_working(true);
    concurrent_queue<std::vector<char> > q;
	
    std::thread producer_thread(std::bind(&producer, std::ref(keep_working), std::ref(q)));
		
    for (size_t i = 0; i != 100000; ++i)
    {
        std::vector<char> tmp;
        q.wait_and_pop(tmp);
    }

    keep_working.store(false);
    producer_thread.join();
    
    return EXIT_SUCCESS;
}

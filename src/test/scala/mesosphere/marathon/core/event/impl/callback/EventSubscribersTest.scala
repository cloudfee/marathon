package mesosphere.marathon.core.event.impl.callback

import mesosphere.marathon.core.event.EventSubscribers
import mesosphere.marathon.Protos
import mesosphere.marathon.test.MarathonSpec

class EventSubscribersTest extends MarathonSpec {

  test("ToProtoEmpty") {
    val v = new EventSubscribers
    val proto = v.toProto

    assert(proto.getCallbackUrlsCount == 0)
    assert(proto.getCallbackUrlsList.size() == 0)
  }

  test("ToProtoNotEmpty") {
    val v = new EventSubscribers(Set("http://localhost:9090/callback"))
    val proto = v.toProto

    assert(proto.getCallbackUrlsCount == 1)
    assert(proto.getCallbackUrlsList.size() == 1)
    assert(proto.getCallbackUrls(0) == "http://localhost:9090/callback")
  }

  test("mergeFromProtoEmpty") {
    val proto = Protos.EventSubscribers.newBuilder().build()
    val subscribers = EventSubscribers()
    val mergeResult = subscribers.mergeFromProto(proto)

    assert(mergeResult.urls == Set.empty[String])
  }

  test("mergeFromProtoNotEmpty") {
    val proto = Protos.EventSubscribers.newBuilder().addCallbackUrls("http://localhost:9090/callback").build()
    val subscribers = EventSubscribers()
    val mergeResult = subscribers.mergeFromProto(proto)

    assert(mergeResult.urls == Set("http://localhost:9090/callback"))
  }
}

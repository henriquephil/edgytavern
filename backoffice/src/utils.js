var dateRegExMs = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})\.{1}\d{1,7}-\d{2}:\d{2}$/;
var dateRegExZ = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})\.{1}\d{1,7}Z$/;
var dateRegEx = /^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})$/;
var dateRegExShort = /^(\d{4})-(\d{2})-(\d{2})$/;

export function tryMatchDate(dateString) {
  if (dateString.length === 19) {
    return dateString.match(dateRegEx);
  }
  if (dateString.length === 10) {
    return dateString.match(dateRegExShort);
  }
  if (dateString.length > 22 && dateString.length < 28) {
    return dateString.match(dateRegExZ);
  }
  if (dateString.length > 26 && dateString.length < 34) {
    return dateString.match(dateRegExMs);
  }
  return false;
};

export function convertDateStringsToDates(input) {
  if (typeof input !== "object")
    return input;
  for (var key in input) {
    if (!input.hasOwnProperty(key))
      continue;

    var value = input[key];
    var match;
    if (typeof value === "string" && (match = tryMatchDate(value))) {
      var date = new Date(match[1], match[2] - 1, match[3], match[4] || 0, match[5] || 0, match[6] || 0);
      input[key] = date;
    } else if (typeof value === "object") {
      convertDateStringsToDates(value);
    }
  }
  return null;
}

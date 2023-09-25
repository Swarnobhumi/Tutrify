import sys
from io import StringIO
from com.chaquo.python import Python

def main(CodeAreaData):
    try:
        # Save the original stdout
        original_stdout = sys.stdout

        # Create a StringIO object to capture the output
        output_buffer = StringIO()
        sys.stdout = output_buffer

        # Redirect sys.stdin to use the provided user_input
        # Executing the Python code
        exec(CodeAreaData)

        # Get the captured output as a string
        output = output_buffer.getvalue()

        # Restore the original stdout and stdin
        sys.stdout = original_stdout
        sys.stdin = sys.__stdin__

    except Exception as e:
        # Restore the original stdout and stdin in case of an exception
        sys.stdout = original_stdout
        sys.stdin = sys.__stdin__
        output = str(e)

    return output
